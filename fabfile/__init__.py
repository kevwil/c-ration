from fabric.api import *
from fabric.utils import *
from fabric.colors import *
from fabric.context_managers import *
from fabric.operations import *
from fabric.contrib.files import *
import os

# default for EC2 images of Ubuntu,
# override with '-u <username>' on command-line
env.user = 'ubuntu'


@task
def deploy(approot='/home/appuser',config_repo='/opt/config'):
  check_remote_setup()
  with settings(hide('warnings'),warn_only=True):
    sudo("stop c-ration")
  update_config(config_repo)
  appdir=approot+"/c-ration"
  with settings(hide('warnings'),warn_only=True):
    sudo("rm -rf "+appdir)
  sudo("mkdir -p "+appdir)

  local("play clean test stage")

  put("target/staged", appdir, use_sudo=True)
  put("target/start", appdir+"/start", use_sudo=True)
  put("README.md", appdir+"/README.md", use_sudo=True)
  sudo("chown -R appuser:appuser "+appdir)
  template_file=os.path.abspath(os.path.join(os.path.dirname(__file__),"c-ration.conf.template"))
  upload_template(template_file, "/etc/init/c-ration.conf",{"appdir":appdir,"config_repo":config_repo},use_sudo=True)
  sudo("chown root:root /etc/init/c-ration.conf")
  sudo("chmod 755 /etc/init/c-ration.conf")
  sudo("start c-ration")

@task
def apply_config(config_repo='/opt/config'):
  update_config(config_repo)
  sudo("restart c-ration")
  
def update_config(config_repo):
  if not exists(config_repo):
    abort("need to create config repo on remote host first")
  with cd(config_repo):
    sudo('git pull')
  
def check_remote_setup():
  with settings(hide('warnings'),warn_only=True):
    run('test -e `which git`')
    run('test -e `which java`')
    run('java -version | grep "1.7"')
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

dist_package_name = 'c-ration-1.0-SNAPSHOT'

@task
def deploy(approot='/home/appuser',config_repo='/opt/config'):
  check_remote_setup()
  with settings(hide('warnings'),warn_only=True):
    sudo("stop c-ration")
  update_config(config_repo)
  local("play clean compile dist")
  if exists(approot+"/"+dist_package_name+".zip"):
    sudo("rm "+approot+"/"+dist_package_name+".zip")
  appdir=approot+"/"+dist_package_name
  if exists(appdir):
    sudo("rm -rf "+appdir)
  put("dist/"+dist_package_name+".zip", approot+"/"+dist_package_name+".zip", use_sudo=True)
  sudo("chown appuser:appuser "+appdir+".zip")
  with cd(approot):
    sudo("su - appuser -c 'unzip "+dist_package_name+".zip'")
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
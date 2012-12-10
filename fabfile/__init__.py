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

env.packagename = 'c-ration-1.0-SNAPSHOT'

@task
def deploy(appdir='/opt',config_repo='/opt/config'):
  check_remote_setup()
  with settings(hide('warnings'),warn_only=True):
    sudo("stop c-ration")
  update_config(config_repo)
  local("play clean compile dist")
  put("dist/"+env.packagename+".zip", appdir, use_sudo=True)
  with cd(appdir):
    sudo("unzip -l "+env.packagename+".zip")
  if not exists("/etc/init/c-ration", use_sudo=True)
    sudo("echo '#!/bin/sh\n\ndescription\t\"configuration server\"\nauthor\t\"Kevin Williams\"\nstart on startup\nexec "+appdir+"/c-ration/start -Dconfigfiles.rootdir="+config_repo+"\n' > /etc/init/c-ration")
  sudo("start c-ration")

@task
def update_config(config_repo)
  with cd(config_repo):
    run('git pull')
  
def check_remote_setup()
  with settings(hide('warnings'),warn_only=True):
    run('test -e `which git`')
    run('test -e `which java`')
    run('java -version | grep "1.7"')
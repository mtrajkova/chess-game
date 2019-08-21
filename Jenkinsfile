pipeline {
    agent any
    stages {
        stage('Clean') {
            steps {
                sh "mvn clean"
            }
        }
        stage('Revert commit'){
            steps {
                sh "git checkout f7f807b81c12409d0e4a5e6642a3dba6bcb56faa"
            }
        }
        stage('Package') {
            steps {
                sh "mvn package"
            }
        }
        
    }
}
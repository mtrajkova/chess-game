pipeline {
    agent any
    stages {
        stage('Clean') {
            steps {
                sh "mvn clean"
            }
        }
        stage('Package') {
            steps {
                sh "mvn package"
            }
        }
        
    }
}
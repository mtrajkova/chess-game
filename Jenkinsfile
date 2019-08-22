pipeline {
    agent any
    stages {
        stage('Clean') {
            steps {
                sh "mvn clean"
            }
        }
        stage('Test') {
            steps {
                sh "mvn test"
            }
        }
        stage('Package') {
            steps {
                sh "mvn package"
            }
        }
        stage('Send over SSH') {
            steps {
                sh "sudo scp -i kpChessGame.pem target/chess-0.0.1-SNAPSHOT.jar ec2-user@ip-192-168-0-165.us-east-2.compute.internal:/home/ec2-user/chess-game"
            }
        }
    }
}
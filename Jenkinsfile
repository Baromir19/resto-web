pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "baromir19/resto-web"
        DOCKER_TAG = "latest"
    }

    tools {
        maven 'maven'
        jdk 'JDK25'
    }

    stages {
        stage('Clean workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Git Checkout') {
            steps {
                script {
                    git branch: 'main',
                        url: 'https://github.com/Baromir19/resto-web.git'
                }
            }
        }

        stage('Build Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }
    }
}
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
                    git branch: 'ci/deploy-jenkins',
                        url: 'https://github.com/Baromir19/resto-web.git'
                }
            }
        }

        stage('Build Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Allure Report') {
            steps {
                bat 'mvn allure:report'
            }
        }

         stage('Build Docker Image') {
            steps {
                bat 'docker build -t %DOCKER_IMAGE%:%DOCKER_TAG% .'
            }
        }

        stage('Login to DockerHub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat """
                    echo %DOCKER_PASS%| docker login -u %DOCKER_USER% --password-stdin
                    """
                }
            }
        }

        stage('Push Image') {
            steps {
                bat """
                docker push %DOCKER_IMAGE%:%DOCKER_TAG%
                """
            }
        }
    }
}
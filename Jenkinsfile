pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'covid-api'
        DOCKER_REGISTRY_TAG = 'jet00000/covid-api:0.0.8'
    }

    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    sh "docker build -t ${DOCKER_IMAGE_NAME} ."
                }
            }
        }

        stage('Tag Docker Image') {
            steps {
                script {
                    // Tag Docker image
                    sh "docker tag ${DOCKER_IMAGE_NAME} ${DOCKER_REGISTRY_TAG}"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Push Docker image to registry
                    sh "docker push ${DOCKER_REGISTRY_TAG}"
                }
            }
        }

        stage('Run Docker Compose') {
            steps {
                script {
                    // Run Docker Compose
                    sh "docker-compose up -d"
                }
            }
        }
    }
}
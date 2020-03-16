pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
                sh 'mvn -U clean package'
            }
        }
        stage('deploy') {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('trigger others') {
            build job: "../ph-ee-connector-ams-mifos", wait: false
            build job: "../ph-ee-connector-channel", wait: false
            build job: "../ph-ee-connector-mojaloop-java", wait: false
        }
    }
}
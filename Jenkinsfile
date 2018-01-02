pipeline {
  agent any
  stages {
    stage('Build & Test') {
      steps {
        sh 'lein test'
      }
    }
    stage('Package') {
      steps {
        sh 'lein uberjar'
        archiveArtifacts(artifacts: 'target/**/*.jar', onlyIfSuccessful: true)
      }
    }
  }
}
pipeline{

  agent { label 'slave-1'}

  tools {
    gradle "Gradle 5.2.1"
  }

  stages {

    stage('Test App'){
      steps{
        echo 'Testing app'
        //sh "gradle clean test"
      }
    }

    stage('Building JS'){
      when {
        expression {
          env.BRANCH_NAME in ["master","QA"]
        }
      }
      steps{
        nodejs(nodeJSInstallationName: 'Node 10.3.0') {
          echo 'Building javascript apps'
          sh 'yarn install'
        }
      }
    }

    stage('Building Applications'){
      steps{
        sh 'gradle build -x test'
      }
    }


    stage('Build image docker') {
      when {
        expression {
          env.BRANCH_NAME in ["master","QA", "PROD"]
        }
      }
      environment {
        SPRING_ENV = "${env.BRANCH_NAME == 'master' ? 'stage' : env.BRANCH_NAME}"
      }
      steps{
        script {
          docker.withTool('Docker') {
            docker.withRegistry('http://localhost:5000') {
              def customImage = docker.build("ebc/springboot/comisiones-li","--build-arg SPRING_ENV=${env.SPRING_ENV} . PATH_FOLDER=build/libs/ .")
              customImage.push()
            }
          }
        }
      }
    }

    stage('DEVL ENVIRONMENT: Transfering sh'){
      when {
        expression {
          env.BRANCH_NAME in ["master", "QA"]
        }
      }
      environment {
        URL_SERVER = "${env.BRANCH_NAME == 'master' ? 'DAWS03LX@172.31.100.25' : 'TAWS02LX@172.31.100.24'}"
      }
      steps{
        sh "scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no deploy.sh ${env.URL_SERVER}:~/deploy_comisiones_li.sh"
      }
    }

    stage('ENVIRONMENT:Deploying app'){
      when {
        expression {
          env.BRANCH_NAME in ["master", "QA"]
        }
      }
      environment {
        URL_SERVER = "${env.BRANCH_NAME == 'master' ? 'DAWS03LX@172.31.100.25' : 'TAWS02LX@172.31.100.24'}"
      }
      steps{
        sh "ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no ${env.URL_SERVER} 'sh deploy_comisiones_li.sh comisiones-li 8107'"
      }
    }
  }

  post{
    success{
      slackSend color: "good", message: "Job: ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} was successful"
    }
    failure{
      slackSend color: "danger", message: "Job: ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} was failed"
    }
    always {
      cleanWs()
    }
  }

}

pipeline{

  agent { label 'slave-1'}

  tools {
    gradle "Gradle 5.2.1"
  }

  stages {

    stage('Test App'){
      steps{
        echo 'Testing app'
        //sh "gradle clean test --stacktrace"
      }
    }

    stage('Building Applications'){
      steps{
        sh 'gradle build -x test'
      }
    }


    stage('Build image docker') {
      //when {
      //  expression {
      //    env.BRANCH_NAME in ["master","QA"]
      //  }
      //}
      steps{
        script {
          docker.withTool('Docker') {
            docker.withRegistry('http://localhost:5000') {
              def customImage = docker.build("ebc/micronaut/comisiones-li",'--build-arg PATH_FOLDER=build/libs/ .')
              customImage.push()
            }
          }
        }
      }
    }

    stage('DEVL ENVIRONMENT: Transfering sh'){
      //when {
      //  expression {
      //    env.BRANCH_NAME in ["master", "QA"]
      //  }
      //}
      environment {
        URL_SERVER = "${env.BRANCH_NAME == 'master' ? 'DAWS03LX@172.31.100.25' : 'DAWS03LX@172.31.100.25'}"
      }
      steps{
        sh "scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no deploy.sh ${env.URL_SERVER}:~/deploy_comisiones_li.sh"
      }
    }

    stage('ENVIRONMENT:Deploying app'){
      //when {
      //  expression {
      //    env.BRANCH_NAME in ["master", "QA"]
      //  }
      //}
      environment {
        URL_SERVER = "${env.BRANCH_NAME == 'master' ? 'DAWS03LX@172.31.100.25' : 'DAWS03LX@172.31.100.25'}"
      }
      steps{
        sh "ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no ${env.URL_SERVER} 'sh deploy_comisiones_li.sh comisiones-li 8105'"
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
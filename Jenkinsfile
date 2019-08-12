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
          env.BRANCH_NAME in ["master","QA","PROD"]
        }
      }
      steps{
        nodejs(nodeJSInstallationName: 'Node 10.3.0') {
          echo 'Building javascript apps'
          sh 'yarn install'
        }
      }
    }

    stage('Transfering properties PROD'){
      when {
        expression {
          env.BRANCH_NAME in ["PROD"]
        }
      }
      steps{
        sh "scp -P 22222 -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no COMPAW@172.31.0.26:/home/COMPAW/.properties-file/application-prod.properties src/main/resources/"
      }
    }

    stage('Building Applications'){
      when {
        expression {
          env.BRANCH_NAME in ["master","QA","PROD"]
        }
      }
      steps{
        sh 'gradle build -x test'
      }
    }


    stage('Build image docker') {
      when {
        expression {
          env.BRANCH_NAME in ["master","QA"]
        }
      }
      environment {
        SPRING_ENV = "${env.BRANCH_NAME == 'master' ? 'stage' : 'qa'}"
      }
      steps{
        script {
          docker.withTool('Docker') {
            docker.withRegistry('http://localhost:5000') {
              def customImage = docker.build("ebc/springboot/comisiones-li","--build-arg SPRING_ENV=${env.SPRING_ENV} --build-arg PATH_FOLDER=build/libs/ .")
              customImage.push()
            }
          }
        }
      }
    }

    stage('Build image docker PROD') {
      when {
        expression {
          env.BRANCH_NAME in ["PROD"]
        }
      }
      steps{
        script {
          docker.withTool('Docker') {
            docker.withRegistry('http://localhost:5000') {
              def customImage = docker.build("ebc/springboot/comisiones-li","--build-arg SPRING_ENV=prod --build-arg PATH_FOLDER=build/libs/ .")
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

    stage('PROD ENVIRONMENT: Transfering sh'){
      when {
        expression {
          env.BRANCH_NAME in ["PROD"]
        }
      }
      steps{
        sh "scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -P 22222 deploy.sh COMPAW@172.31.0.26:~/deploy_comisiones_li.sh"
      }
    }

    stage('PROD ENVIRONMENT:Deploying app'){
      when {
        expression {
          env.BRANCH_NAME in ["PROD"]
        }
      }
      steps{
        sh "ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -p 22222 COMPAW@172.31.0.26 'sh deploy_comisiones_li.sh comisiones-li 9040'"
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

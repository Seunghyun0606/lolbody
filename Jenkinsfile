pipeline {
    agent any
    
    stages {
        // stage('Pull') { 
        //     steps {

        //         sh 'git branch'
        //         sh 'git log'
        //         // git credentialsId: 'git_id', url: 'https://lab.ssafy.com/s03-webmobile1-sub3/s03p13b105.git'
        //         // sh 'git pull origin develop'
        //         // sh 'git checkout develop'
        //     }
        // }
        stage('Build') {
            steps {
                dir('front'){
                    sh 'yarn install'
                    sh 'yarn build'
                    sh 'sudo docker cp dist nginx-deploy:/usr/share/nginx/'
                }
                dir('back/mongoApi'){
                    script{
                        try {
                            sh 'mvn clean package -Dmaven.test.skip=true'
                            sh 'docker build -t spring-image:0.1 .'
                            sh 'docker stop spring-distribute'
                            sh 'docker rm spring-distribute'
                        }catch(e){
                            mattermostSend color: '#439FE0', icon: "https://jenkins.io/images/logos/jenkins/jenkins.png", message: "빌드 실패: ${env.JOB_NAME} ${env.BUILD_NUMBER} ${e}"

                        }
                    }
                    sh 'docker run --name spring-distribute -it -d -p 8888:8888 spring-image:0.1'
                    sh 'docker logs spring-distribute'

                }
                mattermostSend color: '#439FE0', icon: "https://jenkins.io/images/logos/jenkins/jenkins.png", message: "빌드 성공: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
            }
        }
    }  
}

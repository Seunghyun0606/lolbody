pipeline {
    agent any
    
    stages {
        stage('Pull') {
            steps {
              
                sh 'git branch'
                sh 'git log'
                // git credentialsId: 'git_id', url: 'https://lab.ssafy.com/s03-webmobile1-sub3/s03p13b105.git'
                // sh 'git pull origin develop'
                // sh 'git checkout develop'
            }
        }
        stage('Build') {
            steps {
                dir('front'){
                    sh 'yarn install'
                    sh 'yarn build'
                    sh 'sudo docker cp dist nginx-0807:/usr/share/nginx/'
                }
                dir('back/mongoApi'){
                    sh 'mvn clean package -Dmaven.test.skip=true'
                    sh 'docker create --name spring-distribute --entrypoint="java" -it -p 8888:8888 test1 -Djava.security.egd=file:/dev/./urandom -jar *.jar'
                    script{
                        try {
                            
                            sh 'docker stop spring-distribute'
                            sh 'docker rm spring-distribute'
                            sh """
                                sudo docker cp csv/ spring-distribute:/
                                sudo docker cp SummonerValue.py spring-distribute:/
                                sudo docker cp *.jar spring-distribute:/
                            """
                            }catch(e){
                        }
                    }
                    sh 'docker start spring-distribute'

                }
                mattermostSend color: '#439FE0', icon: "https://jenkins.io/images/logos/jenkins/jenkins.png", message: "Build Started: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
            }
        }
    }  
}

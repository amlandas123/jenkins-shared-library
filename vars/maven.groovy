def lintchecks(){
        sh "whoami"
        sh "echo *** Lint check starting for ${component} ***"
        sh "mvn checkstyle:check || true"
        sh "echo *** Lint check completed for ${component} ***"
}

def call(){
        pipeline{
                agent{
                        label "node"
                }
                environment{
                        SONAR_CRED = credentials('sonar_cred')
                }
                tools{
                        maven 'maven396'     
                }
                stages{
                    stage("lintchecks"){
                        steps{
                                script {
                                     lintchecks()           
                                       }
                                }
                
                        }
                    stage("compile code"){
                        steps{
                                sh "mvn clean compile"
                                sh "ls -ltr target/"
                        }
                    }    
                    stage("Static code analysis"){
                        steps{
                                script {
                                        env.ARGS='-Dsonar.java.binaries=./target/'
                                        common.sonarchecks()
                                }
                        }
                        
                    }
                    stage("get the sonar result"){
                        steps{
                                sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > gates.sh"
                                sh "bash gates.sh admin password ${sonar_url} ${component}"
                        }
                    }
                    stage("Test Cases"){
                        parallel{
                                stage("Unit testing"){
                                        steps{
                                                sh "echo Download-1 is in progress"
                                       }
                                }                   
                                stage("Integration testing"){
                                        steps{
                                                sh "echo Download-2 is in progress"
                                        }

                                }
                                stage("Funtional Testing"){
                                        steps{
                                                sh "echo Download-3 is in progress"
                                        }

                                }
                        }             
                }
                stage("Prepare artifacts"){
                        steps{
                                sh "echo creating artifacts"
                        }

                }
                stage("Uploading artifacts"){
                        steps{
                                sh "echo uploading artifacts"
                        }

                }
                
        }
}               
}
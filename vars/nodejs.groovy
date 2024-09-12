def lintchecks(){
        sh "whoami"
        sh "echo *** Lint check starting for ${component} ***"
        sh "npm install jslint"
        sh "node_modules/jslint/bin/jslint.js server.js || true"
        sh "echo *** Lint check completed for ${component} ***"
}



def call(){
        pipeline{
                agent{
                        label "node"
                }
                environment{
                        SONAR_CRED = credentials{'sonar_cred'}
                }
                stages{
                    stage("lintchecks"){
                        steps{
                                script {
                                     lintchecks()           
                                       }
                                }
                
                        }
                    stage("Static code analysis"){
                        steps{
                                script {
                                        env.ARGS='-Dsonar.sources=.'
                                        common.sonarchecks()
                                }
                        }
                        
                    }
                    stage("get the sonar result"){
                        steps{
                                sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > gates.sh"
                                sh "sudo bash gates.sh admin password ${sonar_url} ${component}"
                        }
                    }
                    stage("unit code testing"){
                        steps{
                                sh "echo Testing is in progress"
                        }
                        
                    }        
                }


}
}               
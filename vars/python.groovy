def lintchecks(){
        sh "whoami"
        sh "echo *** Lint check starting for ${component} ***"
        // sh "mvn checkstyle:check || true"
        // sh "pip3 install pylint"
        // ah "pylint *.py || true"
        sh "echo *** Lint check completed for ${component} ***"
}

def sonarchecks(){
        sh '''
        sonar-scanner -Dsonar.host.url=http://172.31.33.165:9000 -Dsonar.java.binaries=./target/ -Dsonar.projectKey=${component} -Dsonar.login=admin -Dsonar.password=password

        '''
}

def call(){
        pipeline{
                agent{
                        label "node"
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
                                        sonarchecks()
                                }
                        }
                        
                    }
                    stage("get the sonar result"){
                        steps{
                                sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > gates.sh"
                                sh "bash gates.sh admin password ${sonar_url} ${component}"
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
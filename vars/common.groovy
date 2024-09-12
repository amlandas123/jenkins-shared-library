def sonarchecks(){
        sh '''
        sonar-scanner -Dsonar.host.url=http://172.31.33.165:9000 ${ARGS} -Dsonar.projectKey=${component} -Dsonar.login=${sonar_cred_USR} -Dsonar.password=${sonar_cred_PSW}

        '''
}
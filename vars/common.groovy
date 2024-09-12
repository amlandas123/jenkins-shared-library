def sonarchecks(){
        sh '''
        sonar-scanner -Dsonar.host.url=http://172.31.33.165:9000 ${ARGS} -Dsonar.projectKey=${component} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW}

        '''
}
void call(Map params) {
    echo "Start - Extension for stage: ${params.stageName}"
    echo "Current stage config: ${params.config}"
    params.originalStage()  // Execute original stage

    // Setup the environment to start the application with CAP server
    // and execute UiVeri5 system tests
    uiVeri5ExecuteTests script: this,
    installCommand: "cds watch",
    // installCommand: "npm install @sap/cds --global --quiet && NPM_CONFIG_PREFIX=/home/node/.npm-global npm install @sap/cds-dk --global --quiet --force && NPM_CONFIG_PREFIX=/home/node/.npm-global npm install @ui5/uiveri5 --global --quiet && npm install --force && (/home/node/.npm-global/lib/node_modules/@sap/cds/bin/cds.js watch > cds.log 2>&1 &)"
    runOptions: ["--seleniumAddress=http://localhost:4444/wd/hub", "./app/admin/webapp/test/uiveri5/conf.js"]
    // runCommand: "sleep 10 && cd /var/jenkins_home/workspace/${JOB_NAME}/app/admin/webapp/test/uiveri5/ && /home/node/.npm-global/lib/node_modules/@ui5/uiveri5/bin/uiveri5 --seleniumAddress='http://selenium:4444/wd/hub' --baseUrl='http://npm:4004/fiori.html#manage-books' -v"
    
    // HTML Publisher plugin
    // Publish HTML reports
    // Publish Test Report for UIveri5 on Jenkins  
    publishHTML target: [
        allowMissing: true,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportDir: '/var/jenkins_home/workspace/${JOB_NAME}/app/admin/webapp/test/uiveri5/target/report/screenshots',
        reportFiles: "report.html",
        reportName: "UIVeri5 Test Report"
    ]

    echo "End - Extension for stage: ${params.stageName}"
}
return this
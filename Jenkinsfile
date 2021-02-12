#!/usr/bin/env groovy 

/*
 * This file bootstraps the codified Continuous Delivery pipeline for extensions of SAP solutions such as SAP S/4HANA.
 * The pipeline helps you to deliver software changes quickly and in a reliable manner.
 * A suitable Jenkins instance is required to run the pipeline.
 * More information on getting started with Continuous Delivery can be found here: https://sap.github.io/jenkins-library/
 */

@Library('piper-lib-os') _

// piperPipeline script: this

node {
    stage("System tests") {
        git credentialsId: 'githubCredentialsId',
            url: scm.userRemoteConfigs[0].url,
            branch: 'main'
            //branch: scm.branches[0].toString().substring(2, scm.branches[0].toString().length())
           
        // Setup the environment to start the application with CAP server
        // and execute UiVeri5 system tests
        uiVeri5ExecuteTests script: this,
        installCommand: "npm install @sap/cds --global --quiet && NPM_CONFIG_PREFIX=/home/node/.npm-global npm install @sap/cds-dk --global --quiet --force && NPM_CONFIG_PREFIX=/home/node/.npm-global npm install @ui5/uiveri5 --global --quiet && npm install --force && cd fiori && (/home/node/.npm-global/lib/node_modules/@sap/cds/bin/cds.js watch > cds.log 2>&1 &)",
        runCommand: "sleep 10 && cd /var/jenkins_home/workspace/${JOB_NAME}/fiori/app/admin/webapp/test/uiveri5/ && /home/node/.npm-global/lib/node_modules/@ui5/uiveri5/bin/uiveri5 --seleniumAddress='http://selenium:4444/wd/hub' --baseUrl='http://npm:4004/fiori.html#manage-books' -v"
        
        // HTML Publisher plugin
        // Publish HTML reports
        // Publish Test Report for UIveri5 on Jenkins  
        publishHTML target: [
            allowMissing: true,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: '/var/jenkins_home/workspace/${JOB_NAME}/fiori/app/admin/webapp/test/uiveri5/target/report/screenshots',
            reportFiles: "report.html",
            reportName: "UIVeri5 Test Report"
        ]
    }
}
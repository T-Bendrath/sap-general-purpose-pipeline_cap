void call(Map params) {
    echo "Start - Extension for stage: ${params.stageName}"
    echo "Current stage config: ${params.config}"
    // params.originalStage()  // Execute original stage

    try {
                // npm i sqlite3@5.0.0 -g --unsafe-perm
                // npm i @sap/cds@4.5.1 -g --quiet
                // npm i @sap/cds-dk@3.4.0 --quiet --force
                // cds build
                // cds serve all --with-mocks --in-memory?
        dockerExecute(script: this, dockerImage: 'node:lts-stretch'){
            sh """
                npm i express@4.17.1 -g --quiet
                npm i @sap/cds -g --quiet
                cds serve all --with-mocks --in-memory?
            """
        }

        echo "Finished UIVeri5Test preparation"

        // Setup the environment to start the application with CAP server and execute UIVeri5 system tests
        uiVeri5ExecuteTests script: this,
            runOptions: ["./app/admin/webapp/test/uiveri5/conf.js"]
            // installCommand: "npm install @sap/cds --global --quiet && NPM_CONFIG_PREFIX=/home/node/.npm-global npm install @sap/cds-dk --global --quiet --force && NPM_CONFIG_PREFIX=/home/node/.npm-global npm install @ui5/uiveri5 --global --quiet && npm install --force && (/home/node/.npm-global/lib/node_modules/@sap/cds/bin/cds.js watch > cds.log 2>&1 &)"
            // runCommand: "cd /home/jenkins/agent/workspace/openSAP-CAP-Pipeline_main/app/admin/webapp/test/uiveri5/ && /home/node/.npm-global/bin/uiveri5"
        
        echo "Finished UIVeri5Test exeuction"

    } finally {

        // Publish Test Report for UIVeri5 on Jenkins
        publishHTML target: [
            allowMissing: true,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: "./app/admin/webapp/test/uiveri5/target/report/screenshots",
            reportFiles: "report.html",
            reportName: "UIVeri5 Test Report"
        ]

        deleteDir()

        echo "End - Extension for stage: ${params.stageName}"
    }
}
return this
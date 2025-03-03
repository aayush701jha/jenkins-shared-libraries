#!/usr/bin/env groovy

def call(Map params) {
    script {
        def timestamp = new Date().format("yyyy-MM-dd_HH-mm-ss")
        def backupFile = "/tmp/${params.dbName}_backup_${timestamp}.sql"

        sh """
        export BACKUP_FILE="${backupFile}"
        PGPASSWORD="${params.dbPassword}" pg_dump -h ${params.dbHost} -U ${params.dbUser} -d ${params.dbName} -p 5432 > $BACKUP_FILE
        aws s3 cp $BACKUP_FILE s3://${params.s3Bucket}/
        """

        echo "✅ Database backup completed and uploaded to S3: ${params.s3Bucket}"
    }
}

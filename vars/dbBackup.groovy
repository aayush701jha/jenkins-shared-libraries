#!/usr/bin/env groovy

def call(String dbHost, String dbUser, String dbPassword, String dbName, String s3Bucket) {
    script {
        def timestamp = new Date().format("yyyy-MM-dd_HH-mm-ss")
        def backupFile = "/tmp/${dbName}_backup_${timestamp}.sql"

        sh """
        export BACKUP_FILE="${backupFile}"
        PGPASSWORD="${dbPassword}" pg_dump -h ${dbHost} -U ${dbUser} -d ${dbName} -p 5432 > $BACKUP_FILE
        aws s3 cp $BACKUP_FILE s3://${s3Bucket}/
        """
        
        echo "Database backup completed and uploaded to S3 bucket: ${s3Bucket}"
    }
}

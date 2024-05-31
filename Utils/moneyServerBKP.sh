echo "STEP1 : backup for moneyserver database started...$(date)"
cd /home/parth/serverUtilsForMoneyServer/backupDATAparth
current_datetime=$(TZ=Asia/Kolkata date +"%Y-%m-%d_%H-%M-%S")
base_filename="moneyServerDBbkp"
filename="$base_filename-$current_datetime-IST"
$(pg_dump -F d -j 4 -C -Z 1 -f "$filename" -d moneyserver -U postgres)
echo "STEP2 : dump dir created: $filename"
$(tar -zcvf "$filename.tar.gz" "$filename")
echo "STEP3 : tar.gz file created for backup: $filename.tar.gz"
echo "STEP4 : starting backup to gdrive for : $filename.tar.gz ....."
$(python3 /home/parth/serverUtilsForMoneyServer/moneyserverBackup.py "$filename.tar.gz")
echo "STEP5 : gdrive backup complete.. "
echo "STEP6 : starting xlsx gdrive backup.."
cd /home/parth/serverUtilsForMoneyServer/backupDATAparthXLSX
$(python3 /home/parth/serverUtilsForMoneyServer/moneyServerApiToXlsxBKP.py "$current_datetime")
echo "STEP7: XLSX gdrive backup complete.. EXITING..."

import os
from google.oauth2 import service_account
from googleapiclient.discovery import build
import sys

# Define the Google Drive API scopes and service account file path
SCOPES = ['https://www.googleapis.com/auth/drive']
SERVICE_ACCOUNT_FILE = "service acc file path"

# Create credentials using the service account file
credentials = service_account.Credentials.from_service_account_file(SERVICE_ACCOUNT_FILE, scopes=SCOPES)


def list_folder(parent_folder_id=None, delete=False):
    """List folders and files in Google Drive."""
    results = drive_service.files().list(
        q=f"'{parent_folder_id}' in parents and trashed=false" if parent_folder_id else None,
        pageSize=1000,
        fields="nextPageToken, files(id, name, mimeType)"
    ).execute()
    items = results.get('files', [])

    if not items:
        print("No folders or files found in Google Drive.")
    else:
        print("Folders and files in Google Drive:")
        for item in items:
            print(f"Name: {item['name']}, ID: {item['id']}, Type: {item['mimeType']}")
            if delete:
                delete_files(item['id'])

def delete_files(file_or_folder_id):
    """Delete a file or folder in Google Drive by ID."""
    try:
        drive_service.files().delete(fileId=file_or_folder_id).execute()
        print(f"Successfully deleted file/folder with ID: {file_or_folder_id}")
    except Exception as e:
        print(f"Error deleting file/folder with ID: {file_or_folder_id}")
        print(f"Error details: {str(e)}")

drive_service = build('drive', 'v3', credentials=credentials)
parent_folder_id = 'id'
parent_folder_xlsx_id = 'id'
resetFLAG = "n"
if len(sys.argv) > 1:
    resetFLAG = str(sys.argv[1])
    print("Got resetFLAG from sys args: "+resetFLAG)
else:
    print("resetFLAG is n -> skipping reset delete")

if resetFLAG.lower() == "y":
    list_folder(parent_folder_id,True)
    print("Delete Successful!")
if resetFLAG.lower() == "yy":
    list_folder(parent_folder_id,True)
    list_folder(parent_folder_xlsx_id,True)
    print("Normal & XLSX Backup Delete Successful!")

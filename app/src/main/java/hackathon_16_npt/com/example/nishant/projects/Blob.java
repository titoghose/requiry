/**package hackathon_16_npt.com.example.nishant.projects;

import android.os.AsyncTask;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
//import com.microsoft.azure.storage.file.FileInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

/**
 * Created by Work on 22/10/16.


import android.os.AsyncTask;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.File;
import java.net.URI;

import hackathon_16_npt.com.example.nishant.projects.RegistrationActivity;

public class Blob extends AsyncTask<String, Void, Void> {

    public static String storageConnectionString;

    private String imgPath;
    private RegistrationActivity act;

    public Blob(RegistrationActivity act, String imgPath) {
        this.imgPath = imgPath;
        this.act = act;
    }

    @Override
    public Void doInBackground(String... arg0) {
        storageConnectionString = "DefaultEndpointsProtocol=https;"
                + "AccountName=requirypics;"
                + "AccountKey=4Ez1VAJr5CvNGxWArHyfs9+kUP978uh+J6ECycTv1K4dnhwYdTjCgio+rmKjc3OPMJmSPn+tufZ6o4HuVl8tfg==";

        try {
            //Creating a reference to a blob account
            CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);

            //Creating a blob service client
            CloudBlobClient blobClient = account.createCloudBlobClient();

            //Creating reference to a container in the blob storage account
            CloudBlobContainer container = blobClient.getContainerReference("profilepics");

            container.createIfNotExists();

            // Make the container public
            // Create a permissions object
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

            // Include public access in the permissions object
            containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

            // Set the permissions on the container
            container.uploadPermissions(containerPermissions);

            CloudBlockBlob blob1 = container
                    .getBlockBlobReference("pic1.jpg");

            File source = new File(imgPath);
            // Upload text to the blob
            blob1.upload(new FileInputStream(source), source.length());

            URI imgURI = blob1.getUri();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
*/


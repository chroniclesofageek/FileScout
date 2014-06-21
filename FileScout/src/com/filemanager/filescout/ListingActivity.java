package com.filemanager.filescout;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListingActivity extends Activity 
{

	final ArrayList<String> listOfFileNames = new ArrayList<String>();
	final ArrayList<File> listOfFileTypes = new ArrayList<File>();
	File mParentDirectory;
	File mGrandParentDirectory;
	ListView listOfFilesview;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLatestFiles();
        
        //Populate the list view
        listOfFilesview = (ListView) findViewById(R.id.lv_listoffiles);
        
        //Create own Adapter
        final File_Array_Adapter adapter = new File_Array_Adapter(getApplicationContext(),listOfFileNames,listOfFileTypes);
        listOfFilesview.setAdapter(adapter);
        
      /*  //Create a list of file types
        for (int i = 0; i < ArrayOfFileTypes.length; ++i) 
        {
        	if(!ArrayOfFileTypes[i].isHidden())
        	{
        		listOfFileTypes.add(ArrayOfFileTypes[i]);
        	}
        }
        
        //Create a list of file names
        for (int i = 0; i < ArrayOfFileNames.length; ++i) 
        {
        	if(!ArrayOfFileTypes[i].isHidden())
        	{	
        		listOfFileNames.add(ArrayOfFileNames[i]);
        	}
        }
                        
        //Create own Adapter
        final Directory_Array_Adapter adapter = new Directory_Array_Adapter(getApplicationContext(),listOfFileNames);
        listview.setAdapter(adapter);*/

        //OnClick Listener for items on the list
        listOfFilesview.setOnItemClickListener(new OnItemClickListener() 
        {
        	  @Override
        	  public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
        	  {
        		  //Launch another Activity to see folder contents
        		  
        		  //If type is a file
        		  if(listOfFileTypes.get(position).isFile())
        		  {
        			  File file = listOfFileTypes.get(position);
        			  MimeTypeMap map = MimeTypeMap.getSingleton();
        			  String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
	    			  String type = map.getMimeTypeFromExtension(ext);
	
	    			  if (type == null)
	    			        type = "*/*";
	
	    			  Intent intent = new Intent(Intent.ACTION_VIEW);
	    			  Uri data = Uri.fromFile(file);
	    			  intent.setDataAndType(data, type);
	    			  startActivity(intent);
        		  }
        		  
        		  //If type is a folder
        		  if(listOfFileTypes.get(position).isDirectory())
        		  {
        			  	
        			  	mParentDirectory = listOfFileTypes.get(position);
        			  	mGrandParentDirectory = mParentDirectory.getParentFile();
        			  
	        			  //File file = listOfFileTypes.get(position);
	        			  //Intent intent = new Intent(getApplicationContext(),ListingActivity.class);
	        			  //intent.putExtra("PathOfItem",file.getPath());
	        			  //startActivity(intent);
        			  
        			  	String[] ArrayOfFileNames = mParentDirectory.list();
        			  	File[] ArrayOfFileTypes = mParentDirectory.listFiles();
        			  	
        			  	//Clearing the file lists
        		        listOfFileTypes.clear();
        		        listOfFileNames.clear();
        			  	
        			  	 //Create a list of file types
        		        for (int i = 0; i < ArrayOfFileTypes.length; ++i) 
        		        {
        		        	if(!ArrayOfFileTypes[i].isHidden())
        		        	{
        		        		listOfFileTypes.add(ArrayOfFileTypes[i]);
        		        	}
        		        }
        		        
	        			//Create a list of file names
	    		        for (int i = 0; i < ArrayOfFileNames.length; ++i) 
	    		        {
	    		        	if(!ArrayOfFileTypes[i].isHidden())
	    		        	{	
	    		        		listOfFileNames.add(ArrayOfFileNames[i]);
	    		        	}
	    		        }
	    		        
	    		        final File_Array_Adapter adapter = new File_Array_Adapter(getApplicationContext(),listOfFileNames,listOfFileTypes);
	    		        listOfFilesview.setAdapter(adapter); 
        		  }
        	  }
        }); 
    }
    
    //My local media scanner. Scan latest files from local storage
    public void getLatestFiles()
    {
    	//Get list of files in internal storage
    	mParentDirectory = Environment.getExternalStorageDirectory();
    	
        String[] ArrayOfFileNames = Environment.getExternalStorageDirectory().list();
        File[] ArrayOfFileTypes = Environment.getExternalStorageDirectory().listFiles();
        
        //Clearing the file lists
        listOfFileTypes.clear();
        listOfFileNames.clear();
        
        //Create a list of file types
        for (int i = 0; i < ArrayOfFileTypes.length; ++i) 
        {
        	if(!ArrayOfFileTypes[i].isHidden())
        	{
        		listOfFileTypes.add(ArrayOfFileTypes[i]);
        	}
        }
        
        //Create a list of file names
        for (int i = 0; i < ArrayOfFileNames.length; ++i) 
        {
        	if(!ArrayOfFileTypes[i].isHidden())
        	{	
        		listOfFileNames.add(ArrayOfFileNames[i]);
        	}
        }
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) 
    {
    	BrowseDirectory(mParentDirectory);
    	super.onConfigurationChanged(newConfig);
    }
    
    public void BrowseDirectory(File directory)

    {
    	String[] ArrayOfFileNames = directory.list();
	  	File[] ArrayOfFileTypes = directory.listFiles();
	  	
	  	//Clearing the file lists
        listOfFileTypes.clear();
        listOfFileNames.clear();
	  	
	  	 //Create a list of file types
        for (int i = 0; i < ArrayOfFileTypes.length; ++i) 
        {
        	if(!ArrayOfFileTypes[i].isHidden())
        	{
        		listOfFileTypes.add(ArrayOfFileTypes[i]);
        	}
        }
        
		//Create a list of file names
        for (int i = 0; i < ArrayOfFileNames.length; ++i) 
        {
        	if(!ArrayOfFileTypes[i].isHidden())
        	{	
        		listOfFileNames.add(ArrayOfFileNames[i]);
        	}
        }
        
        final File_Array_Adapter adapter = new File_Array_Adapter(getApplicationContext(),listOfFileNames,listOfFileTypes);
        listOfFilesview.setAdapter(adapter); 
    }
    
    @Override
    public void onBackPressed() 
    {
    	BrowseDirectory(mGrandParentDirectory);
    	mParentDirectory = mGrandParentDirectory;
    	mGrandParentDirectory = mParentDirectory.getParentFile();
    	//super.onBackPressed();
    }
    
}

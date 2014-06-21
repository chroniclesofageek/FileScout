package com.filemanager.filescout;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainMenuActivity extends Activity 
{
	//List of files
	final ArrayList<String> listOfFileNames = new ArrayList<String>();
	final ArrayList<String> listOfDirectoryNames = new ArrayList<String>();
	final ArrayList<File> listOfFileTypes = new ArrayList<File>();
	boolean isSearchOptionOn = false;
	ListView mListOfDirectoryOptions = null;
	Directory_Array_Adapter mDirectoryAdapter = null;
	File_Array_Adapter mFileAdapter = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Populate the listview
        mListOfDirectoryOptions = (ListView) findViewById(R.id.lv_listoffiles);
        
        //Register for ContextMenu
        //registerForContextMenu(listview);
        
        //List of file directories
        listOfDirectoryNames.add("Local Device Memory");
        listOfDirectoryNames.add("Google Drive");
        listOfDirectoryNames.add("Dropbox");
        
                        
        //Create own Adapter
        mDirectoryAdapter = new Directory_Array_Adapter(getApplicationContext(),listOfDirectoryNames);
        mListOfDirectoryOptions.setAdapter(mDirectoryAdapter);
        
        setClickListenerForList(mListOfDirectoryOptions);
    }

    
    public void setClickListenerForList(ListView viewForListener)
    {
    	 //OnClick Listener for items on the list
    	viewForListener.setOnItemClickListener(new OnItemClickListener() 
        {
        	  @Override
        	  public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
        	  {
        		  
        		  	switch(position)
        		  	{
        		  			//If position is 0 --> Local Device Memory
        		  			case 0:
        		  			Intent browseIntent = new Intent(MainMenuActivity.this, com.filemanager.filescout.ListingActivity.class);
        		  			startActivity(browseIntent);
        		  		    break;
        		  		    
        		  			case 1:
        		  			//Connect to Google Drive
        		  			break;
        		  			
        		  			case 2:
        		  			//Connect to Dropbox
        		  			break;
        		  	}
        	  }
        }); 
    }
}

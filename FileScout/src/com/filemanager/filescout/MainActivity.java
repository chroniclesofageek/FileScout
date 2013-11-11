package com.filemanager.filescout;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	//List of files
	final ArrayList<String> listOfFileNames = new ArrayList<String>();
	final ArrayList<File> listOfFileTypes = new ArrayList<File>();
	boolean isSearchOptionOn = false;
	ListView listview = null;
	SimpleArrayAdapter adapter = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Populate the listview
        listview = (ListView) findViewById(R.id.listView1);
        
        //Register for ContextMenu
        registerForContextMenu(listview);
        
        //Scan local storage for latest files
        getLatestFiles();
                        
        //Create own Adapter
        adapter = new SimpleArrayAdapter(getApplicationContext(),listOfFileNames,listOfFileTypes);
        listview.setAdapter(adapter);

        //OnClick Listener for items on the list
        listview.setOnItemClickListener(new OnItemClickListener() 
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
        			  File file = listOfFileTypes.get(position);
        			  Intent intent = new Intent(getApplicationContext(),ListingActivity.class);
        			  intent.putExtra("PathOfItem",file.getPath());
        			  startActivity(intent);
        		  }
        	  }
        }); 
    }

    //For creation of settings menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    //When option in settings menu is selected
    @SuppressLint("NewApi") @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	
    	switch(item.getItemId())
    	{
    		//When the search option is selected
    		case R.id.search:
    		isSearchOptionOn = true;
    		final ActionBar myActionbar = getActionBar();
    		myActionbar.setCustomView(R.layout.actionbar_search);
    		
    		//Add a listener to the done button of search bar
    		final EditText search = (EditText) myActionbar.getCustomView().findViewById(R.id.searchfield);
    	    search.setOnEditorActionListener(new OnEditorActionListener() 
    	    {
	    	      @Override
	    	      public boolean onEditorAction(TextView v, int actionId,KeyEvent event)
	    	      {
	    	    	  Toast.makeText(MainActivity.this, "Search triggered",Toast.LENGTH_LONG).show();
	    	    	  //Hide Keyboard and reset actionbar
	    	    	  InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    	    	  imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
	    	    	  
	    	    	  myActionbar.setCustomView(null);
	    	    	  return false;
	    	      }
    	    });
    	    
    		myActionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
    		return true;
    		
    		//When about me option is selected by the user
    		case R.id.menu_settings:
    		Intent aboutMeIntent = new Intent(getApplicationContext(),AboutMeActivity.class);
    		startActivity(aboutMeIntent);
    		return true;
    		
    		default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) 
    {
    	getMenuInflater().inflate(R.menu.contextmenu, menu);
    	super.onCreateContextMenu(menu, v, menuInfo);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) 
    {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        View view = info.targetView;
        
    	switch (item.getItemId())
    	{
    		case R.id.copy:
    		return true;
    		
    		case R.id.delete:
    		//getLatestFiles();
    		File toBeDeleted = listOfFileTypes.get(index);
    		DeleteRecursive(toBeDeleted);
    		UpdateList(view,index);
    		return true;
    		
    		case R.id.move:
    		return true;
    		
    		case R.id.rename:
    		return true;
    		
    		case R.id.details:
    		return true;
    		
    		default:
    		return super.onContextItemSelected(item);
    	}
    }
    //My local media scanner. Scan latest files from local storage
    public void getLatestFiles()
    {
    	//Get list of files in internal storage
        String[] ArrayOfFileNames = Environment.getExternalStorageDirectory().list();
        File[] ArrayOfFileTypes = Environment.getExternalStorageDirectory().listFiles();
        
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
    
    @SuppressLint("NewApi") @Override
    public void onBackPressed() 
    {
    	if(isSearchOptionOn)
    	{
    		ActionBar myActionBar = getActionBar();
    		myActionBar.setCustomView(null);
    		myActionBar.setTitle("File Scout");
    		isSearchOptionOn = false;
    	}
    	else
    	{
    		super.onBackPressed();
    	}
    }
    
    public void DeleteRecursive(File toBeDeleted)
    {
    	if (toBeDeleted.isDirectory())
    	{
		    for (File child : toBeDeleted.listFiles())
		    {
		        DeleteRecursive(child);
		    }
    	}

    	boolean isDeleted = toBeDeleted.delete();
    	
    	if(isDeleted)
    	{
    		Toast.makeText(MainActivity.this, "File Deleted",Toast.LENGTH_SHORT).show();
    	}
    	else
    	{
    		Toast.makeText(MainActivity.this, "File Not Deleted",Toast.LENGTH_SHORT).show();
    	}
    }
    
    @SuppressLint("NewApi") public void UpdateList(final View view,final int index)
    {
    	view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() 
        {
          @Override
          public void run() 
          {
            listview.removeViewAt(index);
            adapter.notifyDataSetChanged();
            view.setAlpha(1);
          }
        });
    }
}

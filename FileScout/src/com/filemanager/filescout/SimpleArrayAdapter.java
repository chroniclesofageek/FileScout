package com.filemanager.filescout;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleArrayAdapter extends ArrayAdapter<String> 
{
	private final Context context;
	private final ArrayList<String> nameValues;
	private final ArrayList<File> typeValues;
	
	public SimpleArrayAdapter(Context context, ArrayList<String> values, ArrayList<File> typevalues)
	{
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.nameValues = values;
		this.typeValues = typevalues;
	}

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.secondLine);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    textView.setText(nameValues.get(position));
    
    // Change the icon for Windows and iPhone
    File s = typeValues.get(position);
    
    if (s.isDirectory()) 
    {
    	imageView.setImageResource(R.drawable.folder);
    } 
    else
    {
    	imageView.setImageResource(R.drawable.file);
    }

    return rowView;
  }
}
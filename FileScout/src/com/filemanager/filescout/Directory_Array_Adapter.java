package com.filemanager.filescout;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Directory_Array_Adapter extends ArrayAdapter<String> 
{
	private final Context context;
	private final ArrayList<String> nameValues;
	
	public Directory_Array_Adapter(Context context, ArrayList<String> values)
	{
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.nameValues = values;
	}

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) 
	  {
		  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		  
		  TextView textView = (TextView) rowView.findViewById(R.id.secondLine);
		  ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		  
		  textView.setText(nameValues.get(position));
		  
		  if(position == 0)
		  {
			  imageView.setImageResource(R.drawable.folder_open_icon);
		  }
		  else if(position == 1)
		  {
			  imageView.setImageResource(R.drawable.drive_icon);
		  }
		  else if(position == 2)
		  {
			  imageView.setImageResource(R.drawable.dropbox_icon);
		  }
	
		  return rowView;
	  }
}
package com.mybook.mohamed.mybook.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.mybook.mohamed.mybook.Activities.DetailesActivity;
import com.mybook.mohamed.mybook.Activities.UpdateActivity;
import com.mybook.mohamed.mybook.Data.DatabaseHandler;
import com.mybook.mohamed.mybook.Model.BookDataObject;
import com.mybook.mohamed.mybook.R;
import java.util.List;

public class BookAdaptor extends RecyclerView.Adapter<BookAdaptor.ViewHolder> {

    private Context context;
    private List<BookDataObject> bookDataObjects;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private LayoutInflater layoutInflater;

    public BookAdaptor(Context context, List<BookDataObject> bookDataObjectss) {
        this.context = context;
        this.bookDataObjects = bookDataObjectss;
    }

    @Override
    public BookAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_raw,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(BookAdaptor.ViewHolder holder, int position) {
        BookDataObject currentBook = bookDataObjects.get(position);
        holder.bookName.setText(currentBook.getName());
        holder.author.setText(currentBook.getAuthor());
        //to insure not checked from here
        if(currentBook.getIsDone()== 1){
        holder.done.setChecked(true);//1=done and 0 for not done
            /*if (!holder.done.isChecked()){
                holder.done.setChecked(true);
                Toast.makeText(context,"This Action Not valid Try From Update Screen",Toast.LENGTH_SHORT).show();
            }*/

        }else{
            holder.done.setChecked(false);
           /* if (holder.done.isChecked()){
                holder.done.setChecked(false);
                Toast.makeText(context,"This Action Not valid Try From Update Screen",Toast.LENGTH_SHORT).show();
            }*/


        }

     }

    @Override
    public int getItemCount() {
        return bookDataObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView bookName;
        public TextView author;
        public CheckBox done;
         public Button editBtton,deleteButton;
        public int id;

        public ViewHolder(View itemView,Context ctx) {
            super(itemView);
            context = ctx;
            bookName = (TextView) itemView.findViewById(R.id.listRawTVNameID);
            author = (TextView) itemView.findViewById(R.id.listRawTVAutherID);
            done = (CheckBox) itemView.findViewById(R.id.listRawCBDoneID);
            editBtton = (Button) itemView.findViewById(R.id.listRawBtnEditID);
            deleteButton = (Button) itemView.findViewById(R.id.listRawBtnDeleteID);
            editBtton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            done.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //go to next screen DetailesActivity
                    int position = getAdapterPosition();
                    BookDataObject currentBook = bookDataObjects.get(position);
                     Intent intent = new Intent(context,DetailesActivity.class);
                    intent.putExtra("id", currentBook.getId());
                    intent.putExtra("name", currentBook.getName());
                    intent.putExtra("author", currentBook.getAuthor());
                    intent.putExtra("rate", currentBook.getRate());
                    intent.putExtra("done", currentBook.getIsDone());
                    intent.putExtra("date", currentBook.getDate());
                    intent.putExtra("file", currentBook.getFilePath());
                    context.startActivity(intent);
               // Toast.makeText(context,currentBook.getFilePath()+currentBook.getAuthor(),Toast.LENGTH_LONG).show();

                }
            });


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.listRawBtnEditID:
                    int positionForEdit = getAdapterPosition();
                    BookDataObject currentBookForEdit = bookDataObjects.get(positionForEdit);
                    editItem(currentBookForEdit);


                    break;
                case R.id.listRawBtnDeleteID:
                    int positionForDelete = getAdapterPosition();
                    BookDataObject currentBookForDelete = bookDataObjects.get(positionForDelete);
                     deletItem(currentBookForDelete.getId());
                    break;
                case R.id.listRawCBDoneID:
                    Toast.makeText(context,"This Action Not valid Try From Update Screen",Toast.LENGTH_SHORT).show();
                    if (done.isChecked()){
                        done.setChecked(false);}else done.setChecked(true);
                    break;


            }


        }
        public void deletItem(final int itemId){
            //create an alert dialog
            alertDialogBuilder = new AlertDialog.Builder(context);
            layoutInflater = LayoutInflater.from(context);
            View v = layoutInflater.inflate(R.layout.comfermation_dialoge,null);
            TextView name = (TextView) v.findViewById(R.id.confirmationTv);
            Button no = (Button) v.findViewById(R.id.confirmationBtnNoID);
            Button yes = (Button)v.findViewById(R.id.confirmationBtnYesID);
            alertDialogBuilder.setView(v);
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //delete item from database
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteBook(itemId);
                    bookDataObjects.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    alertDialog.dismiss();
                }
            });

        }
        public void editItem (final BookDataObject bookDataObject){ //go to update activity
            int position = getAdapterPosition();
            BookDataObject currentBook = bookDataObjects.get(position);
            Intent intent = new Intent(context,UpdateActivity.class);
            intent.putExtra("id",currentBook.getId());
            intent.putExtra("name",currentBook.getName());
            intent.putExtra("author",currentBook.getAuthor());
            intent.putExtra("rate",currentBook.getRate());
            intent.putExtra("done",currentBook.getIsDone());
            intent.putExtra("filePath",currentBook.getFilePath());

            context.startActivities(new Intent[]{intent});
        }
    }

}
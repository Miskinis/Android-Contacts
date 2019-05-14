package com.eima.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private int currentSelection = RecyclerView.NO_POSITION;
    private List<Contact> mContacts;

    public ContactsAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    public void AddItem(Contact newContact)
    {
        mContacts.add(newContact);
        notifyItemInserted(getItemCount());
    }

    public void ChangeItem(Contact contact)
    {
        mContacts.set(currentSelection, contact);
        notifyItemChanged(currentSelection);
    }

    public int getCurrentSelection(){ return currentSelection; }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {

        public TextView nameTextView;
        public TextView emailTextView;
        public TextView phoneNumberTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.contact_name);
            emailTextView = itemView.findViewById(R.id.contact_email);
            phoneNumberTextView = itemView.findViewById(R.id.contact_phoneNumber);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(currentSelection);
            currentSelection = getAdapterPosition();
            notifyItemChanged(currentSelection);
        }
        @Override
        public boolean onLongClick(final View view)
        {
            notifyItemChanged(currentSelection);
            currentSelection = getAdapterPosition();
            notifyItemChanged(currentSelection);

            String[] colors = {"Edit", "Delete", "QR Code"};

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Options");
            builder.setItems(colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Context context = view.getContext();
                    Intent intent;
                    Activity activity = (Activity) context;
                    Contact contact;
                    switch(which)
                    {
                        case 0:
                            intent = new Intent(context, InputFormActivity.class);
                            contact = mContacts.get(currentSelection);
                            intent.putExtra("name", contact.getName());
                            intent.putExtra("email", contact.getEmail());
                            intent.putExtra("phoneNumber", contact.getPhoneNumber());
                            intent.putExtra("add", false);
                            activity.startActivityForResult(intent, 1);
                            break;
                        case 1:
                            mContacts.remove(currentSelection);
                            notifyItemRemoved(currentSelection);
                            AppDatabase.getAppDatabase(context).contactDao().delete(mContacts.get(currentSelection - 1));
                            break;
                        case 2:
                            context = view.getContext();
                            intent = new Intent(context, QRcodeActivity.class);
                            activity = (Activity) context;
                            contact = mContacts.get(currentSelection);
                            intent.putExtra("name", contact.getName());
                            activity.startActivityForResult(intent, 0);
                            break;
                    }
                }
            });
            builder.show();
            return true;
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.rv_contact_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Contact contact = mContacts.get(position);

        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(contact.getName());

        TextView emailTextView = viewHolder.emailTextView;
        emailTextView.setText(contact.getEmail());

        TextView phoneNumberTextView = viewHolder.phoneNumberTextView;
        phoneNumberTextView.setText(contact.getPhoneNumber());

        viewHolder.itemView.setSelected(currentSelection == position);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }
}

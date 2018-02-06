package com.ucb.appin.presenters.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ucb.appin.R;
import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.util.UtilInVirtual;

import java.util.List;

/**
 * Created by Alejandro on 8/1/17.
 */

public class MailAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<AvisosBean> list;
    private int TITULO_MAX_LENGHT = 40;
    private int DIRECTION_MAXLENGHT = 80;

    public MailAdapter(Context context, int layout, List<AvisosBean> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AvisosBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            holder = new ViewHolder();
            holder.subject = (TextView) convertView.findViewById(R.id.textViewListSubject);
            holder.message = (TextView) convertView.findViewById(R.id.textViewListMessage);
            holder.sender = (TextView) convertView.findViewById(R.id.textViewListSenderName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        AvisosBean avisosBean = getItem(position);

        // Si el título del correo es más corto de 40 letras, lo dejamos igual, si es más largo,
        // lo cortamos y le añadimos puntos suspensivos al final
        String shortSubject;
        if (avisosBean.getTitulo().length() > TITULO_MAX_LENGHT) {
            shortSubject = avisosBean.getTitulo().substring(0, TITULO_MAX_LENGHT) + "...";
        } else {
            shortSubject = avisosBean.getTitulo();
        }
        holder.subject.setText(shortSubject);

        String shortDirection;
        if (avisosBean.getDireccion().length() > DIRECTION_MAXLENGHT) {
            shortDirection = avisosBean.getDireccion().substring(0, DIRECTION_MAXLENGHT) + "...";
        } else {
            shortDirection = avisosBean.getDireccion();
        }
        holder.message.setText(shortDirection);

        holder.sender.setText(avisosBean.getTitulo().substring(0, 1));
        String ramdomColor = UtilInVirtual.getRandomColor();
        holder.sender.getBackground().setColorFilter(Color.parseColor("#" + ramdomColor), PorterDuff.Mode.SRC);

        return convertView;
    }

    static class ViewHolder {
        private TextView subject;
        private TextView message;
        private TextView sender;
    }
}
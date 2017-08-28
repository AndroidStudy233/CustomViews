package com.greendao.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greendao.demo.entity.User;
import com.greendaodemo.R;

import java.util.ArrayList;
import java.util.List;

/*************************************************
 * <p>类描述：${todo}(用一句话描述该文件做什么)</p>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/8/25</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/

public class UserAdapter extends BaseAdapter {
    private List<User> users;
    @Override
    public int getCount() {
        return users==null?0:users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout,null);
        TextView tvUserId = (TextView) convertView.findViewById(R.id.item_user_id);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.item_user_name);
        TextView tvUserAge= (TextView) convertView.findViewById(R.id.item_user_age);
        TextView tvUserSex= (TextView) convertView.findViewById(R.id.item_user_sex);
        User user = users.get(position);
        tvUserId.setText(user.getId()+"");
        tvUserName.setText(user.getUserName());
        tvUserAge.setText(user.getAge()+"");
        tvUserSex.setText(user.getSex());
        return convertView;
    }
    public void setData(List<User> data){
        if(data!=null&&data.size()>0){
            if(users==null){
                users = new ArrayList<>();
            }
            users.clear();
            users.addAll(data);
            notifyDataSetChanged();
        }
    }
}

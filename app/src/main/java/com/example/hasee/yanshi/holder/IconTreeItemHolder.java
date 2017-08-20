package com.example.hasee.yanshi.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.pojo.NewPojo.ContentTree;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Bogdan Melnychuk on 2/12/15.
 */
public class IconTreeItemHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private TextView arrowView;

    public IconTreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);

        final TextView iconView = (TextView) view.findViewById(R.id.icon);
        iconView.setText(context.getResources().getString(value.icon));

        arrowView = (TextView) view.findViewById(R.id.arrow_icon);

        //if My computer
        if (node.getLevel() == 1) {
            view.findViewById(R.id.btn_delete).setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class IconTreeItem {
        public int icon;
        public String text;
        ContentTree contentTree;

        public IconTreeItem(int icon, String text) {
            this.icon = icon;
            this.text = text;
        }

        public ContentTree getContentTree() {
            return contentTree;
        }

        public void setContentTree(ContentTree contentTree) {
            this.contentTree = contentTree;
        }
    }
}

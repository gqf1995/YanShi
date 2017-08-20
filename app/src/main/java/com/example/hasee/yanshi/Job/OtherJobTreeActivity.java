package com.example.hasee.yanshi.Job;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hasee.yanshi.Base.BaseActivity;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.holder.IconTreeItemHolder;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.ContentObj;
import com.example.hasee.yanshi.pojo.NewPojo.ContentTree;
import com.example.hasee.yanshi.pojo.NewPojo.Event.ContactEvent;
import com.google.gson.Gson;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2017/7/30.
 */

public class OtherJobTreeActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    RelativeLayout container;
    Unbinder unbinder;
    ViewGroup containerView;
    List<ContentTree> contentTrees;
    @BindView(R.id.etSearch)
    EditText etSearch;
    private AndroidTreeView tView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_other_job_tree);
        ButterKnife.bind(this);
        toolbar.setTitle("可查看人员");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        containerView = (ViewGroup) findViewById(R.id.container);
        contentTrees = new ArrayList<>();
        initAddressBook();
    }

    private void initAddressBook() {
        Subscription subscribe_getAddressBook = NetWork.getDemoService().getOtherLeaderWorkBefore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContentObj>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf","contentObj"+e.toString());
                    }

                    @Override
                    public void onNext(ContentObj contentObj) {
                        //Log.i("gqf","contentObj"+contentObj.toString());
                        dealData(contentObj);
                    }
                });
        compositeSubscription.add(subscribe_getAddressBook);
    }
    TreeNode root;

    public void dealData(ContentObj contentObj) {
        root = TreeNode.root();
        for (int i = 0; i < contentObj.getDepartments().size(); i++) {
            //第一层
            IconTreeItemHolder.IconTreeItem iconTreeItem = new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,
                    contentObj.getDepartments().get(i).getText() + "--" + ((
                            contentObj.getDepartments().get(i).getChildNodes() == null) ?
                            "0" : contentObj.getDepartments().get(i).getChildNodes().size() + ""));
            iconTreeItem.setContentTree(contentObj.getDepartments().get(i));
            TreeNode file1 = new TreeNode(iconTreeItem);
            Log.i("gqf", "TreeNode" + contentObj.getDepartments().get(i).getHierarchy());
            addTree(file1, contentObj.getDepartments().get(i));
            root.addChildren(file1);
        }
        tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);
        tView.setDefaultNodeLongClickListener(nodeLongClickListener);
        containerView.addView(tView.getView());
        Log.i("gqf", contentTrees.size() + "TreeNode" + contentTrees.toString());
    }

    public void addTree(TreeNode file, ContentTree contentTree) {
        for (int i = 0; i < contentTree.getChildNodes().size(); i++) {
            IconTreeItemHolder.IconTreeItem iconTreeItem = new IconTreeItemHolder.IconTreeItem(R.string.ic_folder,
                    contentTree.getChildNodes().get(i).getText() + "--" + ((
                            contentTree.getChildNodes().get(i).getChildNodes() == null) ?
                            "0" : contentTree.getChildNodes().get(i).getChildNodes().size() + ""));
            iconTreeItem.setContentTree(contentTree.getChildNodes().get(i));
            TreeNode child = new TreeNode(iconTreeItem);
            file.addChildren(child);
            if (contentTree.getChildNodes().get(i).isHasChildren()) {
                addTree(child, contentTree.getChildNodes().get(i));
            } else {
                contentTrees.add(contentTree.getChildNodes().get(i));
            }

            Log.i("gqf", "TreeNode" + contentTree.getChildNodes().get(i).getHierarchy());
        }
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            //Toast.makeText(getActivity(), "click: " + item.text, Toast.LENGTH_SHORT).show();
            if (item.getContentTree().isHasChildren() == false&&item.getContentTree().getSysUser()!=null) {
                toOtherActivity(new ContactEvent(item.getContentTree().getSysUser()));
            }
        }
    };

    private TreeNode.TreeNodeLongClickListener nodeLongClickListener = new TreeNode.TreeNodeLongClickListener() {
        @Override
        public boolean onLongClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            return true;
        }
    };

    Intent intent;
    public void toOtherActivity(ContactEvent contactEvent){
        Gson gson=new Gson();
        intent=new Intent(OtherJobTreeActivity.this,GetOtherJobActivity.class);
        intent.putExtra("ContactEvent",gson.toJson(contactEvent));
        startActivity(intent);
    }
    @OnClick(R.id.search)
    public void onViewClicked() {
        if(etSearch.getText().toString().equals("")){
            etSearch.setError("请输入人名");
        }else{
            for(int i=0;i<contentTrees.size();i++){
                Log.i("gqf","contactEvents"+contentTrees.get(i));
                if(contentTrees.get(i).getSysUser().getName().equals(etSearch.getText().toString())){
                    toOtherActivity(new ContactEvent(contentTrees.get(i).getSysUser()));
                    return;
                }
            }
            Toast.makeText(this,"没有查询到此人",Toast.LENGTH_SHORT).show();
        }
    }


}

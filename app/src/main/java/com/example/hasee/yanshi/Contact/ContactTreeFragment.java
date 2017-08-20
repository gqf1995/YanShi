package com.example.hasee.yanshi.Contact;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.yanshi.Base.BaseFragment;
import com.example.hasee.yanshi.R;
import com.example.hasee.yanshi.holder.IconTreeItemHolder;
import com.example.hasee.yanshi.netWork.NetWork;
import com.example.hasee.yanshi.pojo.NewPojo.ContentObj;
import com.example.hasee.yanshi.pojo.NewPojo.ContentTree;
import com.example.hasee.yanshi.pojo.NewPojo.Event.ContactEvent;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.greenrobot.eventbus.EventBus;

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
 * Created by hasee on 2017/7/28.
 */

public class ContactTreeFragment extends BaseFragment {


    Unbinder unbinder;
    ViewGroup containerView;
    List<ContentTree> contentTrees;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.search)
    TextView search;
    Unbinder unbinder1;
    private AndroidTreeView tView;

    public static ContactTreeFragment newInstance(String param1) {
        Bundle args = new Bundle();
        ContactTreeFragment fragment = new ContactTreeFragment();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ContactTreeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        containerView = (ViewGroup) view.findViewById(R.id.container);
        contentTrees = new ArrayList<>();
        initData();
        unbinder1 = ButterKnife.bind(this, view);
        return view;
    }

    public void initData() {
        Subscription subscribe_getAddressBook = NetWork.getDemoService().getAddressBook()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContentObj>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("gqf", "contentObj" + e.toString());
                    }

                    @Override
                    public void onNext(ContentObj contentObj) {
                        //Log.i("gqf", "contentObj" + contentObj.toString());
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
        tView = new AndroidTreeView(getActivity(), root);
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
                EventBus.getDefault().post(new ContactEvent(item.getContentTree().getSysUser()));
            }
        }
    };

    private TreeNode.TreeNodeLongClickListener nodeLongClickListener = new TreeNode.TreeNodeLongClickListener() {
        @Override
        public boolean onLongClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            Toast.makeText(getActivity(), "Long click: " + item.text, Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

    @OnClick(R.id.search)
    public void onViewClicked() {
        if(etSearch.getText().toString().equals("")){
            etSearch.setError("请输入人名");
        }else{
            for(int i=0;i<contentTrees.size();i++){
                Log.i("gqf","contactEvents"+contentTrees.get(i));
                if(contentTrees.get(i).getSysUser().getName().equals(etSearch.getText().toString())){
                    EventBus.getDefault().post(contentTrees.get(i));
                    return;
                }
            }
            Toast.makeText(getActivity(),"没有查询到此人",Toast.LENGTH_SHORT).show();
        }
    }
}

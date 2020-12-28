package com.ff.common.util;

import java.util.ArrayList;
import java.util.List;

import com.ff.common.model.JsonTreeData;

public class TreeNodeUtil {

    public final static List<JsonTreeData> getfatherNode(List<JsonTreeData> treeDataList) {
        List<JsonTreeData> newTreeDataList = new ArrayList<JsonTreeData>();
        for (JsonTreeData jsonTreeData : treeDataList) {
            if(jsonTreeData.getPid() .equals("")||jsonTreeData.getPid() .equals("0")) {
                //获取父节点下的子节点
                jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(),treeDataList));
                jsonTreeData.setState("open");
                newTreeDataList.add(jsonTreeData);
            }
        }
        return newTreeDataList;
    }

    private final static List<JsonTreeData> getChildrenNode(String pid , List<JsonTreeData> treeDataList) {
        List<JsonTreeData> newTreeDataList = new ArrayList<JsonTreeData>();
        for (JsonTreeData jsonTreeData : treeDataList) {
            if(jsonTreeData.getPid() == null)  continue;
            //这是一个子节点
            if(jsonTreeData.getPid().equals(pid)){
                //递归获取子节点下的子节点
                jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId() , treeDataList));
                newTreeDataList.add(jsonTreeData);
            }
        }
        return newTreeDataList;
    }


    public final static List<JsonTreeData> getfather(List<JsonTreeData> trees) {

        List<JsonTreeData> newTreeDataList = new ArrayList();
        for (JsonTreeData tree : trees) {
            if(tree.getPid().equals("")||tree.getPid() .equals("0")) {
                //获取父节点下的子节点
                newTreeDataList.add(tree);
                getChildren(tree.getId(),trees,newTreeDataList);


            }
        }
        return newTreeDataList;
    }

    private final static void getChildren(String pid , List<JsonTreeData> trees,List<JsonTreeData> newTrees) {

        for (JsonTreeData tree : trees) {
            if(tree.getPid() == null)  continue;
            //这是一个子节点
            if(tree.getPid().equals(pid)){
                //递归获取子节点下的子节点
                newTrees.add(tree);
                getChildren(tree.getId() , trees,newTrees);

            }
        }

    }
}
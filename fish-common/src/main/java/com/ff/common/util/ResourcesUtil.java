package com.ff.common.util;

import com.ff.common.model.ResourcesTree;

import java.util.ArrayList;
import java.util.List;

public class ResourcesUtil {

    public final static List<ResourcesTree> getfatherNode(List<ResourcesTree> resourcesTree) {

        List<ResourcesTree> newTreeDataList = new ArrayList();
        for (ResourcesTree tree : resourcesTree) {
            if(tree.getPid().equals("")) {
                //获取父节点下的子节点
                tree.setChildren(getChildrenNode(tree.getId(),resourcesTree));
                newTreeDataList.add(tree);

            }
        }
        return newTreeDataList;
    }

    private final static List<ResourcesTree> getChildrenNode(String pid , List<ResourcesTree> resourcesTree) {
        List<ResourcesTree> newTreeDataList = new ArrayList();
        for (ResourcesTree tree : resourcesTree) {
            if(tree.getPid() == null)  continue;
            //这是一个子节点
            if(tree.getPid().equals(pid)){
                //递归获取子节点下的子节点
                tree.setChildren(getChildrenNode(tree.getId() , resourcesTree));
                newTreeDataList.add(tree);
            }
        }
        return newTreeDataList;
    }


    public final static List<ResourcesTree> getfather(List<ResourcesTree> resourcesTree) {

        List<ResourcesTree> newTreeDataList = new ArrayList();
        for (ResourcesTree tree : resourcesTree) {
            if(tree.getPid().equals("")) {
                //获取父节点下的子节点
                newTreeDataList.add(tree);
                getChildren(tree.getId(),resourcesTree,newTreeDataList);


            }
        }
        return newTreeDataList;
    }

    private final static void getChildren(String pid , List<ResourcesTree> resourcesTree,List<ResourcesTree> newResourcesTree) {

        for (ResourcesTree tree : resourcesTree) {
            if(tree.getPid() == null)  continue;
            //这是一个子节点
            if(tree.getPid().equals(pid)){
                //递归获取子节点下的子节点
                newResourcesTree.add(tree);
                getChildren(tree.getId() , resourcesTree,newResourcesTree);

            }
        }

    }
}
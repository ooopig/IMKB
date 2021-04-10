import { getRequest } from "./api";
import {resetRouter} from "@/router";
/*
 * 对菜单路由进行处理
 * */
export const initMenu = (router, store) => {
    if (store.state.routes.length > 0) {
        console.log("store中存有routes信息");
        return;
    }
    getRequest("/menu").then(data => {
        if (data) {
            let fmtRoutes = formatRoutes(data);
            //添加路由
            router.addRoutes(fmtRoutes)
            //初始化数据
            store.commit('initRoutes', fmtRoutes);
        }
    })
}
// *
// 菜单数据的格式话 *
export const formatRoutes = (routes) => {
    let fmRoutes = [];
    routes.forEach(router => {
        let {
            path,
            component,
            name,
            iconcls,
            children,
        } = router;
        if (children && children instanceof Array) {
            //递归
            children = formatRoutes(children);
        }
        let fmRouter = {
            path: path,
            name: name,
            iconcls: iconcls,
            children: children,
            meta:{
                //
                title: '智能制造知识工程系统'
            },
        }
        if(component.startsWith("Home")){
            fmRouter.component = resolve =>require(['../views/' + component + '.vue'], resolve);
        }
        else if (component.startsWith("Knowledge")) {
            fmRouter.component = resolve =>require(['../views/expert/' + component + '.vue'], resolve);
        } else if (component.startsWith("Kb")) {
            fmRouter.component = resolve =>require(['../views/kb/' + component + '.vue'], resolve);
        } else if (component.startsWith("Manage")) {
            fmRouter.component = resolve =>require(['../views/manage/' + component + '.vue'], resolve);
        } else if (component.startsWith("User")) {
            fmRouter.component = resolve =>require(['../views/user/' + component + '.vue'], resolve);
        }
        fmRoutes.push(fmRouter);
    })
    return fmRoutes;
}

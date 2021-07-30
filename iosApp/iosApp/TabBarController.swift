//
//  TabBarController.swift
//  iosApp
//
//  Created by Anna Bukreeva on 30.07.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct TabBarController : View {
    
    let searchVC = SearchWindowView()
    let databaseVC = EntriesDatabaseView()

    let tabBarController = UITabBarController()
//    tabBarController.viewControllers = [searchVC, databaseVC]
//    tabBarController.selectedViewController = databaseVC
    var body : some View {
        Text("hi")
    }
}

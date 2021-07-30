//
//  SearchWindowView.swift
//  iosApp
//
//  Created by Anna Bukreeva on 30.07.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SearchWindowView : View {
    @State private var searchText = ""
    @ObservedObject var storage = EntriesStorage()
    
    var body : some View{
        NavigationView {
            SearchBar(text : $searchText)
            NavigationLink(destination: EntryCollectionView()) {
                Button(action: {
                    storage.fetchRepositories(query : searchText)
                }, label: {
                    Text("Search!")
                })
            }
        }
    }
}


// Database view + search window that leads to the results display. the results get added to the database and are then displayed in the database view. tab bar controller to switch between the


// that's some garbage. that's not gonna work

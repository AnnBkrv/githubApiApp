//
//  EntriesDatabaseView.swift
//  iosApp
//
//  Created by Anna Bukreeva on 30.07.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


struct EntriesDatabaseView: View {
    
    @ObservedObject var entries = EntriesStorage() // another entries variable maybe?
    @State var plusButtonTapped = false
    @State private var searchText = ""

    var body: some View {
        NavigationView {
//            List(entries.entries.filter { searchText.isEmpty || $0.name.localizedStandardContains(searchText)}){_ in
            List{
                SearchBar(text : $searchText)
                ForEach(entries.entries , id: \.self) {
                    entry in
                    NavigationLink(destination:
                                    EntryView(entry : entry)) {
                        Text(entry.name)
                    }
                }
            }
        }
    }
}

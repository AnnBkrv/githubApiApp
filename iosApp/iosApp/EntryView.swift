//
//  EntryView.swift
//  iosApp
//
//  Created by Anna Bukreeva on 13.07.21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


struct EntryView : View {
    
    var entry : Repository

    var body : some View {
//        Text(entry.creator.capitalized)
        Text(entry.name.capitalized)
        Text(rating(entry: entry))
        Text(entry.description_?.capitalized ?? "No Description Available")
    }

    func rating (entry : Repository) -> String {
        if entry.stars == nil {
            return "No Rating Available"
        }
        if entry.stars == 0 {
            return "✰"
        }
        else {
            return String(repeating: "⭐️", count: entry.stars as! Int)
        }
    }
    
}

func ??<T>(lhs: Binding<Optional<T>>, rhs: T) -> Binding<T> {
    Binding(
        get: { lhs.wrappedValue ?? rhs },
        set: { lhs.wrappedValue = $0 }
    )
}

//struct ItemAddView : View {
//    
//    @ObservedObject var store : EntriesStorage
//    
//    @State var name = ""
//    @State var creator = ""
//    @State var descr : String? = nil
////    @State var stars : Int? = nil
//    @State var stars : Int = 0
//    
//
//    var body: some View {
//        Form {
//            Section {
//                TextField("Enter the repository's name", text: $name)
//                TextField("Enter the creator", text: $creator)
//                TextField("Enter the description", text: $descr ?? "")
////                Picker("", selection: $stars) {
////                    ForEach(1...5, id : \.self){
////                        Text("\($0)")
////                    }
////                }
//            }
//        }
//        Picker("", selection: $stars) {
//            ForEach(1...5, id : \.self){
//                Text("\($0)")
//            }
//        }
//        Button(action: {
//            let newItem = Repository(name: name, description_: descr, stars: stars as! KotlinInt)
//            store.entries.append(newItem)
//            }) {
//              HStack {
//                Image(systemName: "plus.circle.fill")
//                Text("Add new item")
//              }
//        }.padding()
//    
//    }
//}

//func test () {
//    GithubApiCall().githubApiCall(completionHandler: { repos , _ in
//        guard let repos = repos else { return }
//
//        print(repos)
//    })
//}

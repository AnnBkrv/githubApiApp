import SwiftUI
import shared


struct EntryCollectionView: View {
    
    @ObservedObject var entries = EntriesStorage()
    @State var plusButtonTapped = false

	var body: some View {
        NavigationView {
            List {
                ForEach(entries.entries , id: \.self) {
                    entry in
                    NavigationLink(destination:
                                    EntryView(entry : entry)) {
                        Text(entry.name)
                    }
                }
            }
//            .navigationBarItems(trailing: Button(action: {
//                plusButtonTapped = true
//            }, label: {
//                Image(systemName: "plus").imageScale(.large)
//            })).sheet(isPresented : $plusButtonTapped){
//                ItemAddView(store: entries)
//            }
        }
    }
}

//struct ContentView_Previews: PreviewProvider {
//	static var previews: some View {
//	EntryCollectionView()
//	}
//}


struct GitHubEntry : Hashable {
    
    let id = UUID()
    let name : String
    let descr : String?
    let creator : String
    let stars : Int?
    
}

extension GitHubEntry : Identifiable { }


class EntriesStorage : ObservableObject {
    @Published var entries : [GitHubEntry] = []
    
    init( ){
        
        GithubRemoteImpl().githubApiCall(completionHandler: { repos , _ in
            guard let repos = repos else { return }
            let reposMapped = repos.map { repo in
                GitHubEntry(name: repo.full_name, descr: repo.description_, creator: String(repo.id), stars: Int(repo.stargazers_count)/20)
            }
            self.entries.append(contentsOf: reposMapped) // an escaping closure -> self. needed. the results of the closure are used outside of the closure
            // self. so the entries var doesn't get deleted according to automatic reference counting
        })
        
    }
    
}

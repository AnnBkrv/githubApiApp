import SwiftUI
import shared


// MARK: Here the search results are displayed

struct EntryCollectionView: View {
    
    @ObservedObject var entries = EntriesStorage()
    @State private var searchText = ""
    //@State var entries = [Repository]()

	var body: some View {
        NavigationView {
            List {
//                SearchBar(text : $searchText, entries : entries.entries) // after the search button has been pressed, the entries here need to change
                // TODO: Once searchText has been changed, the feed should be refreshed and the results added to the sql database. all the results should be in the database, but only the new ones should show. Maybe there should be a separate database view with a search within the database and the search/ results view.
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



class EntriesStorage : ObservableObject {
    @Published var entries: [Repository] = []
    
    private let service: GithubService = GithubServiceImpl(
        cache: GithubCacheImpl(driverFactory: DriverFactory()),
        remote: GithubRemoteImpl()
    )
    
    init() {
        fetchRepositories(query: "memorize")
    }
    
    func fetchRepositories(query: String) {
        // [weak self] is a capture list. It tells Swift that we want to use these objects in the closure so they have to stay in memory.
        // weak tells Swift to only keep a weak reference to object so it`s basically optional
        service.getRepos(query: query, completionHandler: { [weak self] repos , _ in
            guard let self = self, let repos = repos else { return }
            self.entries = repos
        })
    }
    
}

import SwiftUI

struct TextFieldView: View {
    var value: Binding<String>
    let hint: String
    
    init(_ hint: String, value: Binding<String>) {
        self.value = value
        self.hint = hint
    }
    
    var body: some View {
        TextField(text: value) {
            Text(hint)
        }
        .padding()
        .background(Color.secondary.opacity(0.35))
        .clipShape(RoundedRectangle(cornerRadius: 25.0))
    }
}

#Preview {
    TextFieldView("Enter your name", value: .constant(""))
}

import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-compiler',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './compiler.html',
  styleUrls: ['./compiler.css']
})
export class CompilerComponent {

  code = `public class Main {
    public static void main(String[] args) {
        System.out.println("Hello from Java!");
    }
}`;

  output = "";

  constructor(private http: HttpClient) {}

  run() {
    this.output = "Running...";

    this.http.post<any>('http://localhost:8080/api/execute', {
      sourceCode: this.code,
      input: "" // You don't need input
    }).subscribe({
      next: (res) => {
        if (res.stderr && res.stderr.trim().length > 0) {
          this.output = "Error:\n" + res.stderr;
        } else {
          this.output = res.stdout;
        }
      },
      error: (err) => {
        console.error(err);
        this.output = "Error connecting to backend: " + err.message;
      }
    });
  }
}

import { Routes } from '@angular/router';
import { CompilerComponent } from './compiler/compiler';

export const routes: Routes = [
  { path: '', component: CompilerComponent },     // default route
  { path: 'compiler', component: CompilerComponent }
];

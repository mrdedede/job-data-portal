import { Component } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.css']
})
export class SearchbarComponent {
  jobForm = new FormGroup({
    jobTitle: new FormControl('')
  })

  constructor() { }

  onSubmit() {
    console.log(this.jobForm)
  }
}

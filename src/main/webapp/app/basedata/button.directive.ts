import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[jhiButton]',
})
export class ButtonDirective {
  constructor(private el: ElementRef) {}

  @HostListener('mouseenter') onMouseEnter(): void {
    this.el.nativeElement.style.boxShadow = 'inset 0 0 0 0.3rem #393a34';
    this.el.nativeElement.style.fontSize = '3rem';
    this.el.nativeElement.style.fontWeight = 'bold';
  }

  @HostListener('mouseleave') onMouseLeave(): void {
    this.el.nativeElement.style.boxShadow = 'none';
    this.el.nativeElement.style.fontSize = '2rem';
    this.el.nativeElement.style.fontWeight = 'normal';
  }
}

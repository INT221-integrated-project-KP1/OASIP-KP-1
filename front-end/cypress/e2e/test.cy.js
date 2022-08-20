describe('OASIP', () => {
  it('passes', () => {
    cy.visit('http://localhost:3000');
    cy.get('.max-w-md > .btn').click()
  })
})
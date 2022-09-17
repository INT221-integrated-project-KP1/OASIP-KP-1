describe("OASIP KP1", () => {
  const pbi24data = [
    {
      'name':'PBI24 สมส่วน สุขศรี 1',
      'email':'somsuan.s241@kmutt.ac.th',
      'role':'Student',
      'pwd':'somsuans'
    },
    {
      'name':'PBI24 สมส่วน สุขศรี 2',
      'email':'somsuan.s242@kmutt.ac.th',
      'role':'Student',
      'pwd':'somsuans123456'
    },
    {
      'name':'PBI24 สมส่วน สุขศรี 3',
      'email':'somsuan.s243@kmutt.ac.th',
      'role':'Student',
      'pwd':'somsuan'
    }
  ]

  it('ADD-USER-WITH-PASSWORD-test case data', () => {
    cy.visit('http://localhost:3000/kp1/SignUp')
    pbi24data.forEach((ele)=>{
      cy.get(':nth-child(1) > .w-full').type(ele.name)
      cy.get(':nth-child(2) > .w-full').type(ele.email)
      cy.get(':nth-child(3) > .w-full').select(ele.role)
      cy.get(':nth-child(4) > .w-full').type(ele.pwd)
      cy.get(':nth-child(5) > .w-full').type(ele.pwd)
      cy.get(':nth-child(6) > .w-full').click()
      cy.wait
    })
  })
  
})